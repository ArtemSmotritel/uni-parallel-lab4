with Ada.Text_IO; use Ada.Text_IO;
with GNAT.Semaphores; use GNAT.Semaphores;

procedure Lab4 is

   FORKS_COUNT : constant Integer := 5;
   AVAILABLE_EATING_SEATS : constant Integer := 2;
   Forks : array (1..FORKS_COUNT) of Counting_Semaphore(1, Default_Ceiling);

   Eating_Philosophers : Counting_Semaphore(AVAILABLE_EATING_SEATS, Default_Ceiling);

   task type WaitingPhilosopher is
      entry Start(Id : Integer);
   end WaitingPhilosopher;

   task body WaitingPhilosopher is
      Id : Integer;
      Id_Left_Fork, Id_Right_Fork : Integer;
   begin
      accept Start (Id : in Integer) do
         WaitingPhilosopher.Id := Id;
      end Start;

      Id_Left_Fork := Id;
      Id_Right_Fork := Id rem FORKS_COUNT + 1;

      for I in 1..10 loop
         Put_Line("Philosopher [" & Id'Img & "] STARTS EATING");
         Put_Line("Phylosopher " & Id'Img & " thinking " & I'Img & " time");

         Eating_Philosophers.Seize;

         Forks(Id_Left_Fork).Seize;
         Put_Line("Phylosopher " & Id'Img & " took left fork");

         Forks(Id_Right_Fork).Seize;
         Put_Line("Phylosopher " & Id'Img & " took right fork");

         Put_Line("Phylosopher " & Id'Img & " eating" & I'Img & " time");

         Forks(Id_Right_Fork).Release;
         Put_Line("Phylosopher " & Id'Img & " put right fork");

         Forks(Id_Left_Fork).Release;
         Eating_Philosophers.Release;
         Put_Line("Phylosopher " & Id'Img & " put left fork");
         Put_Line("Philosopher [" & Id'Img & "] CEASED EATING");
      end loop;

   end WaitingPhilosopher;


   task type Phylosopher is
      entry Start(Id : Integer);
   end Phylosopher;

   task body Phylosopher is
      Id : Integer;
      Id_Left_Fork, Id_Right_Fork : Integer;
   begin
      accept Start (Id : in Integer) do
         Phylosopher.Id := Id;
      end Start;

      if Id = FORKS_COUNT then
         Id_Right_Fork := Id;
         Id_Left_Fork := Id rem FORKS_COUNT + 1;
      else
         Id_Left_Fork := Id;
         Id_Right_Fork := Id rem FORKS_COUNT + 1;
      end if;

      for I in 1..10 loop
         Put_Line("Phylosopher " & Id'Img & " thinking " & I'Img & " time");

         Forks(Id_Left_Fork).Seize;
         Put_Line("Phylosopher " & Id'Img & " took left fork");

         Forks(Id_Right_Fork).Seize;
         Put_Line("Phylosopher " & Id'Img & " took right fork");

         Put_Line("Phylosopher " & Id'Img & " eating" & I'Img & " time");

         Forks(Id_Right_Fork).Release;
         Put_Line("Phylosopher " & Id'Img & " put right fork");

         Forks(Id_Left_Fork).Release;
         Put_Line("Phylosopher " & Id'Img & " put left fork");
         Put_Line("Philosopher [" & Id'Img & "] CEASED EATING");
      end loop;
   end Phylosopher;

   --  Phylosophers : array (1..FORKS_COUNT) of Phylosopher;
   WaitingPhilosophers : array (1..FORKS_COUNT) of WaitingPhilosopher;
Begin
   --  for I in Phylosophers'Range loop
   --     Phylosophers(I).Start(I);
   --  end loop;

   for I in WaitingPhilosophers'Range loop
      WaitingPhilosophers(I).Start(I);
   end loop;

end Lab4;
